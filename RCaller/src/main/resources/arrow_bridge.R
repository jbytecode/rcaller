require("arrow")

send_by_arrow <- function(obj, name, uri) {
  print(typeof(obj))
  if (is.data.frame(obj)) {
    #Export by Arrow as is
  }
  if (is.array(obj)) {
    if (is.matrix(obj)){
      #Convert flat matrix to jagged (list of vectors) that is supported by Arrow
      #Export as Arrow table with one 'fixed-size-list' column
    }
    if (length(dim(obj)) > 2) {
      #3- and more-D arrays are not supported
    }
    if (length(dim(obj)) == 1) {
      #1-D array should be converted to Vector
    }
  }
  if (is.list(obj)){
    #Export as Arrow table with one 'list' column
    #Suppose that each element has the same type
    #Union typed and nested lists are not supported
  }

  if (is.vector(obj)) {

  }
}
