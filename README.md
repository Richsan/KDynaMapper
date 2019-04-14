## KDynaMapper - Kotlin DynamoDB Mapper

####  The Kotlin library that makes your life easier to work with Amazon DynamoDB sdk.


Are you working with DynamoDB, trying to use the java AWS SDK and having to write a lot of boilerplate to map your object from/to the boring Map<String,AttributeValue> data structure?

If so, KDynaMapper comes to solve your problem!

With one call you can generate, from your data class objects or pojos, a Map<String,AttributeValue> equivalent object.


Look how this works:

```kotlin
data class SimpleObject(val msg : String,
                        val number : Int)

 val obj =  SimpleObject("this is a demonstration", 42)

// TADA! with just this call, you obtain your Map<String,AttributeValue> object
val attributeValueObj : Map<String,AttributeValue> = KDynaMapper.mapToDynamoObjectRequest(obj)

// And obtain your SimpleObject back from a Map<String,AttributeValue> with this one call
val simpleObj : SimpleObject = KDynaMapper.fromDynamoMapResponse(attributeValueObj, SimpleObject::class)
```

Works with data classes or normal class objects that contains attributes of following types:

- Integer
- Double
- Float
- Boolean
- java.time.Instant
- java.util.UUID
- java.math.BigInteger
- java.math.BigDecimal
- A List<T> where T is any valid KDynaMapper type
- A Map<String, T> where T is any other valid KDynaMapper type
- An object with attributes of some of above type values. 


We are still working on annotations that mark some of attribute to be ignored from KDynaMapper.

And on unit tests.

There is a lot of work to be done yet.

If you wanna improve this library with some new functionality, fell free to make a PR or address it with an issue, we're gonna love it!
