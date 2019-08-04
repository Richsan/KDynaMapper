package io.github.richsan

import io.github.richsan.javacode.SampleClass
import org.junit.Test
import richsan.KDynaMapper
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class JavaClassTest {
    @Test fun testSomeJavalass() {
        val javaObj = SampleClass()
        val dynaMap = KDynaMapper.mapToDynamoObjectRequest(javaObj)
        validateDynaMapAgainstSimpleObject(dynaMap, javaObj)
        val newObj = KDynaMapper.fromDynamoMapResponse(dynaMap, SampleClass::class.java)
        assertEquals(javaObj, newObj, "Object deserialized differs from original source object")

    }

    private fun validateDynaMapAgainstSimpleObject(dynaMap : Map<String, AttributeValue>, obj: SampleClass) {
        assertEquals(obj.msg, dynaMap["msg"]?.s(), "Differs at msg field")
        assertEquals(obj.number.toString(), dynaMap["number"]?.n(), "Differs at number field")
        assertEquals(obj.bigNum.toString(), dynaMap["bigNum"]?.n(), "Differs at bigNum field")
        assertEquals(obj.bigDec.toString(), dynaMap["bigDec"]?.n(), "Differs at bigDec field")
        assertEquals(obj.uuid.toString(), dynaMap["uuid"]?.s(), "Differs at uuid field")
        assertEquals(obj.valid, dynaMap["valid"]?.bool(), "Differs at valid field")
        assertEquals(obj.time.toString(), dynaMap["time"]?.s(), "Differs at time field")
        assertEquals(obj.floatNum.toString(), dynaMap["floatNum"]?.n(), "Differs at floatNum field")
        assertEquals(obj.doubleNum.toString(), dynaMap["doubleNum"]?.n(), "Differs at doubleNum field")
        assertTrue(dynaMap["nullList"]?.nul()!!)
        assertTrue(dynaMap["nullNumber"]?.nul()!!)
        assertTrue(dynaMap["nullUUID"]?.nul()!!)
    }

}