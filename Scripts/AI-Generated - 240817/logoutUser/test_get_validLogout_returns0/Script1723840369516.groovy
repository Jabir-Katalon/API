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

def customerPayload = '{"id": 100001, "username": "testUser", "address": []}'
def userPayload = '{"id": 11, "username": "testUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "12345", "phone": "12345", "userStatus": 1}'

def createUserRequest = findTestObject('createUser')
addHeaderConfiguration(createUserRequest)
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
createUserRequest.setBodyContent(createUserPayload)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def logoutUserRequest = findTestObject('logoutUser')
addHeaderConfiguration(logoutUserRequest)
def logoutUserResponse = WSBuiltInKeywords.sendRequest(logoutUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(logoutUserResponse, 200)

def verifyStatusCodeRequest = findTestObject('logoutUser')
addHeaderConfiguration(verifyStatusCodeRequest)
def verifyStatusCodeResponse = WSBuiltInKeywords.sendRequest(verifyStatusCodeRequest)
WSBuiltInKeywords.verifyResponseStatusCode(verifyStatusCodeResponse, 0)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

