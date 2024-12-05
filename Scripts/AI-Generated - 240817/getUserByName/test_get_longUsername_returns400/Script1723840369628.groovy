import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def addHeaderConfiguration(request) {
    def content_type_header = new TestObjectProperty("content-type", ConditionType.EQUALS, "application/json")
    request.getHttpHeaderProperties().add(content_type_header)
}

uuid = UUID.randomUUID().toString()

def testObjects = [
    {
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/thisIsAVeryLongUsernameThatExceedsTheLimit/getUserByName",
        "endpoint": "/user/thisIsAVeryLongUsernameThatExceedsTheLimit",
        "method": "get"
    },
    {
        "testObjectId": "Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/thisIsAVeryLongUsernameThatExceedsTheLimit/deleteUser",
        "endpoint": "/user/thisIsAVeryLongUsernameThatExceedsTheLimit",
        "method": "delete"
    }
]

testObjects.each { testObject ->
    def request = findTestObject(testObject.testObjectId)
    addHeaderConfiguration(request)

    if (testObject.method == "get") {
        def response = WSBuiltInKeywords.sendRequest(request)
        WSBuiltInKeywords.verifyResponseStatusCode(response, 400)
    }
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

