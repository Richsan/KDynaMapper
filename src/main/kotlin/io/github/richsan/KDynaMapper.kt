/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package richsan

import org.objenesis.ObjenesisStd
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.jvm.internal.Lambda
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.jvmErasure


/**
 * @author Henrique Sebastiao
 * @since 08/03/19 14:52
 * @version $Revision: $<br/>
 *          $Id: $
 *
 */
object KDynaMapper {


    fun mapToDynamoObjectRequest(objectValue: Any): Map<String, AttributeValue> {

        val objClass = objectValue::class

        val props = objClass.declaredMemberProperties

        val dynamoDbRequestObj: MutableMap<String, AttributeValue> = mutableMapOf()

        props.forEach {
            val objValue = it.call(objectValue)

            if (objValue !is Lambda<*>)
                dynamoDbRequestObj[it.name] = mapSingle(objValue)
        }
        return dynamoDbRequestObj
    }

    fun fromDynamoMapResponse(dynamoDbResponse: Map<String, AttributeValue>, responseClass: KClass<*>): Any? {

        if(dynamoDbResponse.entries.isEmpty())
            return null

        val objInstance = ObjenesisStd().newInstance(responseClass.java)

        dynamoDbResponse.entries.forEach {
            setObjectPropertyIfExists(objInstance, it.key, it.value)
        }

        return objInstance
    }

    private fun mapSingle(objValue: Any?): AttributeValue {

        if (objValue == null)
            return AttributeValue.builder().nul(true).build()

        if (objValue is List<*>) {
            val attList: MutableList<AttributeValue> = mutableListOf()

            objValue.forEach {
                attList.add(mapSingle(it))
            }

            return AttributeValue.builder().l(attList).build()
        }


        if (isNumberValue(objValue))
            return AttributeValue.builder().n(objValue.toString()).build()



        return when (objValue) {

            is String -> AttributeValue.builder().s(objValue).build()

            is Boolean -> AttributeValue.builder().bool(objValue).build()

            is UUID -> AttributeValue.builder().s(objValue.toString()).build()

            is Instant -> AttributeValue.builder().s(formatToISO8601(objValue)).build()
            else -> {
                if (objValue::class.javaPrimitiveType == null)
                    return AttributeValue.builder().m(mapToDynamoObjectRequest(objValue)).build()
                else
                    throw Exception("Unsuported type attribute: ${objValue::class.starProjectedType}")
            }
        }


    }

    private fun isNumberValue(objValue: Any): Boolean {
        return (objValue is Int || objValue is Long
                || objValue is Float || objValue is Double
                || objValue is BigDecimal || objValue is BigInteger)
    }


    private fun setObjectPropertyIfExists(obj: Any, propertyName: String, attrValue: AttributeValue) {
        val property = obj::class.declaredMemberProperties.find { it.name == propertyName }
                ?: return


        val javaField = property.javaField
        javaField?.isAccessible = true



        if (property.returnType.jvmErasure.isSubclassOf(List::class) && javaField != null) {

            val listType = property.returnType.arguments.first().type!!.jvmErasure

            val contentList = mutableListOf<Any?>()

            attrValue.l().forEach {
                contentList.add(getValueFromAttributeValue(listType, it))
            }

            javaField.set(obj, contentList)

            return
        }

        if (property is KMutableProperty<*>) {
            property.setter.call(obj, getValueFromAttributeValue(property.returnType.jvmErasure, attrValue))
            return
        }


       /* val modifiersField = Field::class.java!!.getDeclaredField("modifiers")
        modifiersField.isAccessible = true
        if (javaField != null)
            modifiersField.setInt(javaField, javaField.modifiers and Modifier.FINAL.inv())*/

        javaField?.set(obj, getValueFromAttributeValue(property.returnType.jvmErasure, attrValue))
    }

    private fun getValueFromAttributeValue(attributeClass: KClass<*>, attributeValue: AttributeValue?): Any? {

        if (attributeValue == null)
            return null

        if (isNumberClass(attributeClass))
            return getNumberAttributeValue(attributeClass, attributeValue)




        return when (attributeClass) {
            String::class -> attributeValue.s()

            Boolean::class -> attributeValue.bool()

            UUID::class -> attributeValue.s()?.let {
                UUID.fromString(it)
            }

            Instant::class -> attributeValue.s()?.let {
                Instant.parse(it)
            }

            else -> {
                if (attributeClass.javaPrimitiveType == null)
                    return fromDynamoMapResponse(attributeValue.m(), attributeClass)
                else
                    throw Exception("Unsuported type attribute: ${attributeClass::class.starProjectedType}")
            }

        }

    }

    private fun isNumberClass(objClass: KClass<*>): Boolean {
        return (objClass == Int::class || objClass == Long::class
                || objClass == Float::class || objClass == Double::class
                || objClass == BigDecimal::class || objClass == BigInteger::class)
    }

    private fun getNumberAttributeValue(attributeClass: KClass<*>, attributeValue: AttributeValue): Any? {

        val numberValue: String = attributeValue.n() ?: return null

        return when (attributeClass) {
            Int::class -> numberValue.toInt()
            UInt::class -> numberValue.toUInt()
            Long::class -> numberValue.toLong()
            ULong::class -> numberValue.toULong()

            Float::class -> numberValue.toFloat()
            Double::class -> numberValue.toDouble()

            BigInteger::class -> numberValue.toBigInteger()
            BigDecimal::class -> numberValue.toBigDecimal()

            else -> throw Exception("invalid number type $attributeClass")
        }

    }

    private fun formatToISO8601(time: Instant?): String? {

        if (time == null)
            return null


        return DateTimeFormatter.ISO_INSTANT.format(time)
    }
}
