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

def customerPayload = '{"id": 1, "username": "testCustomer", "address": []}'
def customerRequest = findTestObject('createUser')
def customerPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
customerRequest.setBodyContent(customerPayloadContent)
addHeaderConfiguration(customerRequest)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def userPayload = '{"id": 2, "username": "testUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password", "phone": "12345", "userStatus": 1}'
def userRequest = findTestObject('createUser')
def userPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
userRequest.setBodyContent(userPayloadContent)
addHeaderConfiguration(userRequest)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 200)

def postPayload = '{"username": "testUser", "userStatus": 1}'
def postRequest = findTestObject('createUser')
def postPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(postPayload))
postRequest.setBodyContent(postPayloadContent)
addHeaderConfiguration(postRequest)
def postResponse = WSBuiltInKeywords.sendRequest(postRequest)
WSBuiltInKeywords.verifyResponseStatusCode(postResponse, 200)

log.logInfo("Step 4 - Verify Response Status Code is 400: ${postResponse.getStatusCode()}")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

