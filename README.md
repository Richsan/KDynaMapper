## KDynaMapper - Kotlin DynamoDB Mapper

####  The Kotlin library that makes your life easier to work with Amazon DynamoDB sdk.



Are you working with DynamoDB, trying to use the java AWS SDK and having a lot of boilerplates to map your object from/to the boring Map<String,AttributeValue> data structure?

So, the KDynaMapper comes to solve your problem!

With one call you can generate from/to your data class objects or pojos, an Map<String,AttributeValue> equivalent object.



Look how this works:

```kotlin
data class SimpleObject(val msg : String,
                        val number : Int)

 val obj =  SimpleObject("this is a demonstration", 42)

//TADA! with just this call, you can obtain your Map<String,AttributeValue> object
val attributeValueObj : Map<String,AttributeValue> = KDynaMapper.mapToDynamoObjectRequest(obj)

//And obtain your SimpleObject back from a Map<String,AttributeValue> with this one call
val simpleObj : SimpleObject = KDynaMapper.fromDynamoMapResponse(attributeValueObj, SimpleObject::class)


```

Works with data class or normal class object that contains attributes of following types:

- Integer
- Double
- Float
- Boolean
- java.time.Instant
- java.util.UUID
- java.math.BigInteger
- java.math.BigDecimal
- A List<T> where T is any valid KDynaMapper type.
- An object with attributes of some of above type values. 



We are still working to make compatible with some other usefull values types , like Map<String,T>.

And for anotations that mark some of attribute to be ignored from KDynaMapper.

And unit test.

Thats a lot of work to be done yet.

If you wanna improve this library with some new functionality, fell free to make a PR or address it with an issue, we're gonna love it!

