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

def createUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/createUser')
addHeaderConfiguration(createUserRequest)

def customer_payload = '{"id": 9999, "username": "testUser__unique__", "address": []}'
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customer_payload))
createUserRequest.setBodyContent(createUserPayload)

def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def deleteUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_username_/deleteUser', ['username': 'testUser'])
addHeaderConfiguration(deleteUserRequest)

def deleteUserResponse = WSBuiltInKeywords.sendRequest(deleteUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteUserResponse, 404)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

