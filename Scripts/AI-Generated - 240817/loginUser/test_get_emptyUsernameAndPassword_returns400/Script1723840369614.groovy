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

def customerPayload = '{"id": 100001, "username": "emptyUser", "address": []}'
def createUserPayload = '{"id": 10, "username": "emptyUser", "firstName": "Mike", "lastName": "Taylor", "email": "mike.taylor@email.com", "password": "passwordxyz", "phone": "9998887777", "userStatus": 1}'

def createCustomerRequest = findTestObject('createUser')
def createUserRequest = findTestObject('createUser')
def getUserLoginRequest = findTestObject('loginUser')

def createCustomerPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(createUserPayload))

addHeaderConfiguration(createCustomerRequest)
addHeaderConfiguration(createUserRequest)
addHeaderConfiguration(getUserLoginRequest)

createCustomerRequest.setBodyContent(createCustomerPayload)
createUserRequest.setBodyContent(createUserPayload)

def createCustomerResponse = WSBuiltInKeywords.sendRequest(createCustomerRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
def getUserLoginResponse = WSBuiltInKeywords.sendRequest(getUserLoginRequest)

WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(getUserLoginResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

