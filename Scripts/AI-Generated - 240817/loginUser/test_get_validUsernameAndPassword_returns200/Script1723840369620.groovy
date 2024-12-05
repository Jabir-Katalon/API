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

def customerRequest = findTestObject('createUser')
def customerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 100001, "username": "testUser", "address": []}'))
customerRequest.setBodyContent(customerPayload)
addHeaderConfiguration(customerRequest)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def userRequest = findTestObject('createUser')
def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 10, "username": "testUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'))
userRequest.setBodyContent(userPayload)
addHeaderConfiguration(userRequest)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 200)

def loginRequest = findTestObject('loginUser')
def loginParams = ['username': 'testUser', 'password': 'password123']
def loginRequestWithParams = findTestObject('loginUser', loginParams)
addHeaderConfiguration(loginRequestWithParams)
def loginResponse = WSBuiltInKeywords.sendRequest(loginRequestWithParams)
WSBuiltInKeywords.verifyResponseStatusCode(loginResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

