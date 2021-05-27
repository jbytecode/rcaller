# Calling R from within Clojure

Clojure is one of the most popular JVM languages. RCaller can directly be used in a Clojure project. Firstly, add the RCaller dependency in your ```project.clj``` file:

```clojure
:dependencies [[org.clojure/clojure "1.10.1"]
                 [com.github.jbytecode/RCaller "3.0.2"]]
```

The primary Java classes ```RCaller``` and ```RCode``` can be included in the project using

```clojure
(ns rcallertest.core
  (:import [com.github.rcaller.rstuff RCaller RCode])
  (:gen-class))
```

RCaller interacts with ```R``` interpreter in two ways. The first one simply passes the ```R``` code and gets back the calculated results. The ```R``` process is terminated after performing calculations. The second one hangs an ```R``` connection in the memory so sequential calls are faster using these pre-started ```R``` process.

Here is a single pass examle. In this example, an ```R``` process is created, calculations are performed, and the process is terminated:

```clojure
(defn runAndResult [code x y]
  (let [rcaller (RCaller/create)
        rcode (RCode/create)
        parser (.getParser
                (do
                  (.addRCode rcode code)
                  (.setRCode rcaller rcode)
                  (.runAndReturnResult rcaller x)
                  rcaller))
        result (.getAsDoubleArray parser y)]
    result))
```

Now, we send an ```R``` code and get back the results:

```clojure
(runAndResult 
"
x <- rnorm(10)
m = mean(x)
s = sd(x)
result <- list(m = m, s = s)
" 
 "result" "m")
```

```x``` is an random vector drawn from a standard Normal distribution. ```m``` and ```s``` are arithmetic mean and standard deviation of ```x```, respectively. ```result``` is a ```list``` that holds these values. The ```runAndResult``` ```Clojure``` function extracts the value ```m``` from the returned list ```result```. 
