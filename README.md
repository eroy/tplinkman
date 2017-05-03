# TPLink-Man


## About project

Applications for management, administration and monitoring of routers Tp-Link.


### Architecture specs

Here we've used [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) pattern, which allows us to separate view elements from the logic and retrieving data.
In _View_ we contain only View elements, lists, specific Android parts which need to be displayed to user. Also we have reference on Presenter class.
In _Model_ we've implemented retrieving of data with specific methods which needs Context for perform.
In _Presenter_ we contain references on Model, View and UseCase classes. Here we implement all the logic that is required for concrete View.


### Used Technologies

* Retrofit 2
https://github.com/square/retrofit
* Okhttp
https://github.com/square/okhttp
* RxAndroid
https://github.com/ReactiveX/RxAndroid
* Realm database
https://github.com/realm/realm-java


### License

[Apache-2.0](https://github.com/eroy/tplinkman/blob/master/LICENCE.txt)
