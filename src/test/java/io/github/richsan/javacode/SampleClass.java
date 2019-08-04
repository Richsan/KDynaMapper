package io.github.richsan.javacode;

import io.github.richsan.Person;
import io.github.richsan.PersonGenerator;
import io.kotlintest.properties.Gen;
import kotlin.random.Random;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

public class SampleClass {
    private String msg;
    private int number;
    private BigInteger bigNum;
    private BigDecimal bigDec;
    private UUID uuid;
    private Boolean valid;
    private Instant time;
    private float floatNum;
    private double doubleNum;
    private List<Person> objList;
    private SampleClass nestedObject;
    private List<Integer> nullList;
    private Integer nullNumber;
    private UUID nullUUID;
    private Map<String,String> mapStrStr;
    private Map<String, Person> mapStrPerson;

    public SampleClass() {

        setMsg(genRandomString());
        setNumber(genRandomInt());
        setBigNum(BigInteger.valueOf(genRandomInt()));
        setBigDec(BigDecimal.valueOf(genRandomDouble()));
        setUuid(UUID.randomUUID());
        setValid(genRandomBool());
        setTime(Instant.now());
        setFloatNum((float)genRandomDouble());
        setDoubleNum(genRandomDouble());
        setObjList(Gen.Companion.list(new PersonGenerator()).random().iterator().next());
        setMapStrStr(Gen.Companion.map(Gen.Companion.string(), Gen.Companion.string()).random().iterator().next());
        setMapStrPerson(Gen.Companion.map(Gen.Companion.string(), new PersonGenerator()).random().iterator().next());


    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigInteger getBigNum() {
        return bigNum;
    }

    public void setBigNum(BigInteger bigNum) {
        this.bigNum = bigNum;
    }

    public BigDecimal getBigDec() {
        return bigDec;
    }

    public void setBigDec(BigDecimal bigDec) {
        this.bigDec = bigDec;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public float getFloatNum() {
        return floatNum;
    }

    public void setFloatNum(float floatNum) {
        this.floatNum = floatNum;
    }

    public double getDoubleNum() {
        return doubleNum;
    }

    public void setDoubleNum(double doubleNum) {
        this.doubleNum = doubleNum;
    }

    public List<Person> getObjList() {
        return objList;
    }

    public void setObjList(List<Person> objList) {
        this.objList = objList;
    }

    public SampleClass getNestedObject() {
        return nestedObject;
    }

    public void setNestedObject(SampleClass nestedObject) {
        this.nestedObject = nestedObject;
    }

    public List<Integer> getNullList() {
        return nullList;
    }

    public void setNullList(List<Integer> nullList) {
        this.nullList = nullList;
    }

    public Integer getNullNumber() {
        return nullNumber;
    }

    public void setNullNumber(Integer nullNumber) {
        this.nullNumber = nullNumber;
    }

    public UUID getNullUUID() {
        return nullUUID;
    }

    public void setNullUUID(UUID nullUUID) {
        this.nullUUID = nullUUID;
    }

    public Map<String, String> getMapStrStr() {
        return mapStrStr;
    }

    public void setMapStrStr(Map<String, String> mapStrStr) {
        this.mapStrStr = mapStrStr;
    }

    public Map<String, Person> getMapStrPerson() {
        return mapStrPerson;
    }

    public void setMapStrPerson(Map<String, Person> mapStrPerson) {
        this.mapStrPerson = mapStrPerson;
    }

    private String genRandomString() {
        return Gen.Companion.string().random().iterator().next();
    }

    private int genRandomInt() {
        return (int)(Math.random());

    }

    private double genRandomDouble() {
        return Math.random();

    }

    private boolean genRandomBool() {
        return Gen.Companion.bool().random().iterator().next();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleClass)) return false;
        SampleClass that = (SampleClass) o;
        return getNumber() == that.getNumber() &&
                Float.compare(that.getFloatNum(), getFloatNum()) == 0 &&
                Double.compare(that.getDoubleNum(), getDoubleNum()) == 0 &&
                Objects.equals(getMsg(), that.getMsg()) &&
                Objects.equals(getBigNum(), that.getBigNum()) &&
                Objects.equals(getBigDec(), that.getBigDec()) &&
                Objects.equals(getUuid(), that.getUuid()) &&
                Objects.equals(getValid(), that.getValid()) &&
                Objects.equals(getTime(), that.getTime()) &&
                Objects.equals(getObjList(), that.getObjList()) &&
                Objects.equals(getNestedObject(), that.getNestedObject()) &&
                Objects.equals(getNullList(), that.getNullList()) &&
                Objects.equals(getNullNumber(), that.getNullNumber()) &&
                Objects.equals(getNullUUID(), that.getNullUUID()) &&
                Objects.equals(getMapStrStr(), that.getMapStrStr()) &&
                Objects.equals(getMapStrPerson(), that.getMapStrPerson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMsg(), getNumber(), getBigNum(), getBigDec(), getUuid(), getValid(), getTime(), getFloatNum(), getDoubleNum(), getObjList(), getNestedObject(), getNullList(), getNullNumber(), getNullUUID(), getMapStrStr(), getMapStrPerson());
    }
}
