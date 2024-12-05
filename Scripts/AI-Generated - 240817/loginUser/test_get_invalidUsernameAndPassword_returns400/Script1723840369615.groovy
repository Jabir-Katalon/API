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

def customerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 100001, "username": "invalidUser__unique__", "address": []}'))
def createUserRequest = findTestObject('createUser')
createUserRequest.setBodyContent(customerPayload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 11, "username": "invalidUser", "firstName": "Jane", "lastName": "Smith", "email": "jane.smith@email.com", "password": "password456", "phone": "9876543210", "userStatus": 1}'))
def addUserRequest = findTestObject('createUser')
addUserRequest.setBodyContent(userPayload)
addHeaderConfiguration(addUserRequest)
def addUserResponse = WSBuiltInKeywords.sendRequest(addUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addUserResponse, 200)

def loginParams = ['username': 'invalidUser', 'password': 'wrongPassword']
def loginRequest = findTestObject('loginUser', ['username': loginParams.username])
addHeaderConfiguration(loginRequest)
def loginResponse = WSBuiltInKeywords.sendRequest(loginRequest)
WSBuiltInKeywords.verifyResponseStatusCode(loginResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

