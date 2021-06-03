require("arrow")

send_element_by_arrow <- function(obj, name, stream) {
  if (is.data.frame(obj)) {
    #Export by Arrow as is
    write_ipc_stream(obj, stream)
    return()
  }
  if (is.array(obj)) {
    if (is.matrix(obj) && dim(obj)[1] > 0 && dim(obj)[2] > 0){
      #Export filled matrix as Arrow table with one 'fixed-size-list' column
      #Convert flat matrix to jagged (list of vectors) that is supported by Arrow
      matrix_jagged_fixedsize <- list()
      rows <- dim(obj)[1]
      columns <- dim(obj)[2]
      for (i in 1:rows) {
        matrix_jagged_fixedsize[[i]] <- obj[i,]
      }
      #Create temporary redcord batch, rows type would be autodetected, matrix type would be 'list'
      batch_typedetect <- record_batch(matrix_column=matrix_jagged_fixedsize)
      #Convert 'list' type to 'fixed-size-list'
      schema_fixed_size <- schema(matrix_column=fixed_size_list_of(type = batch_typedetect$schema$fields[[1]]$type$value_type, columns))
      batch <- record_batch(matrix_column=matrix_jagged_fixedsize, schema=schema_fixed_size)
      names(batch) <- name
      write_ipc_stream(batch, stream)
      return()
    }
    if (length(dim(obj)) > 2) {
      #3- and more-D arrays are not supported
      stop(paste(length(dim(obj)), "-D arrays are not supported"))
      #TODO add try-catch support on toplevel
    }
    #1-D array and empty matrix can be converted to Vector
    dim(obj) <- c()
  }
  if (is.list(obj)){
    if (length(obj) > 0 && length(names(obj)) == 0) {
      #Export as Arrow table with one 'list' column
      #Suppose that each element has the same type
      #Union typed and nested lists might not work
      batch <- record_batch(list_column=obj)
      names(batch) <- name
      write_ipc_stream(batch, stream)
      return()
    } else if (length(names(obj)) > 0) {
      #Export each field separatly
      i <- 0
      for (subj_name in names(obj)) {
        i <- i + 1
        subj <- obj[[i]]
        send_element_by_arrow(subj, cleanNames(paste0(name, "$", subj_name)), stream)
      }
    } else {
      #Convert empty List to empty Vector
      obj <- unlist(obj)
    }
  }

  if (typeof(obj)=="language") {
    obj<-toString(obj)
  } else if(typeof(obj)=="logical") {
    obj<-as.character(obj)
  } else if(class(obj)=="factor") {
    obj<-as.vector(obj)
  }

  if (is.vector(obj) && length(obj) > 0) {
    #export filled vector with auto type detect
    batch <- record_batch(vector_column=obj)
    names(batch) <- name
    write_ipc_stream(batch, stream)
    return()
  } else if (length(obj) == 0) {
    #export empty element
    obj <- c(1)
    type_example_batch <- record_batch(empty_column=obj)
    length(obj) <- 0
    empty_batch <- record_batch(empty_column=obj, schema=type_example_batch$schema)
    write_ipc_stream(empty_batch, stream)
    return()
  # } else {
  #   stop("Probably unsupported output")
  }
}

send_by_arrow <- function(obj, name, uri_result) {
  stream <- FileOutputStream$create(uri_result)
  if (is.list(obj) && length(names(obj)) > 0) {
    i <- 0
    for (subj_name in names(obj)) {
      i <- i + 1
      subj <- obj[[i]]
      send_element_by_arrow(subj, cleanNames(subj_name), stream)
    }
  } else {
    send_element_by_arrow(obj, cleanNames(name), stream)
  }
  stream$close()
}
