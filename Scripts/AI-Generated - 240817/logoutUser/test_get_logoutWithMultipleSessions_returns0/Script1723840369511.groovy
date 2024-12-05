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

def customerPayload = '{"id": 100001, "username": "multiSessionUser__unique__", "address": []}'
def userPayload = '{"id": 11, "username": "multiSessionUser__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "12345", "phone": "12345", "userStatus": 1}'

def createUserRequest = findTestObject('createUser')
def createCustomerRequest = findTestObject('createUser')

def createCustomerPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))

addHeaderConfiguration(createCustomerRequest)
addHeaderConfiguration(createUserRequest)

createCustomerRequest.setBodyContent(createCustomerPayload)
createUserRequest.setBodyContent(createUserPayload)

def createCustomerResponse = WSBuiltInKeywords.sendRequest(createCustomerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)

def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def getUserLoginRequest = findTestObject('loginUser')
def getUserLogoutRequest = findTestObject('logoutUser')

addHeaderConfiguration(getUserLoginRequest)
addHeaderConfiguration(getUserLogoutRequest)

def getUserLoginResponse = WSBuiltInKeywords.sendRequest(getUserLoginRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getUserLoginResponse, 200)

def getUserLogoutResponse = WSBuiltInKeywords.sendRequest(getUserLogoutRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getUserLogoutResponse, 200)

assert getUserLogoutResponse.getResponseText() == "0"

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

