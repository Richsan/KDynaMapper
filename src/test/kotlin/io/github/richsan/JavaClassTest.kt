package io.github.richsan

import io.github.richsan.javacode.SampleClass
import org.junit.Test
import richsan.KDynaMapper
import kotlin.test.assertEquals

class JavaClassTest {
    @Test fun testSomeJavalass() {
        val javaObj = SampleClass()
        val dynaMap = KDynaMapper.mapToDynamoObjectRequest(javaObj)
        val newObj = KDynaMapper.fromDynamoMapResponse(dynaMap, SampleClass::class.java)
        assertEquals(javaObj, newObj, "Object deserialized differs from original source object")

    }

}