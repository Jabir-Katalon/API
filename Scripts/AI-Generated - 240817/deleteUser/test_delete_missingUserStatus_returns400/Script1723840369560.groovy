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

def customerPayload = '{"id": 9999, "username": "testUser", "address": []}'
def userPayload = '{"id": 9999, "username": "testUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password123", "phone": "1234567890"}'

def createUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/createUser')
def deleteUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_username_/deleteUser')

def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
def deleteUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(''))

addHeaderConfiguration(createUserRequest)
addHeaderConfiguration(deleteUserRequest)

createUserRequest.setBodyContent(createUserPayload)
deleteUserRequest.setBodyContent(deleteUserPayload)

def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def deleteUserResponse = WSBuiltInKeywords.sendRequest(deleteUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteUserResponse, 400)

println(deleteUserResponse.getStatusCode())

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

