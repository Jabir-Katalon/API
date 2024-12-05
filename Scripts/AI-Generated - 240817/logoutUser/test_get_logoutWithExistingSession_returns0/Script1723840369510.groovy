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

def customerPayload = '{"id": 100001, "username": "existingUser", "address": []}'
def userPayload = '{"id": 11, "username": "existingUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "12345", "phone": "12345", "userStatus": 1}'

def loginRequest = findTestObject('loginUser')
addHeaderConfiguration(loginRequest)
def loginResponse = WSBuiltInKeywords.sendRequest(loginRequest)
WSBuiltInKeywords.verifyResponseStatusCode(loginResponse, 200)

def logoutRequest = findTestObject('logoutUser')
addHeaderConfiguration(logoutRequest)
def logoutResponse = WSBuiltInKeywords.sendRequest(logoutRequest)
WSBuiltInKeywords.verifyResponseStatusCode(logoutResponse, 200)

assert logoutResponse.getStatusCode() == 0

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

