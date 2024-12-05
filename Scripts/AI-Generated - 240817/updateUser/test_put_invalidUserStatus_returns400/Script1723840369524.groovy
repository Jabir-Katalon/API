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

def customerPayload = '{"id": 100001, "username": "invalidStatusCustomer__unique__", "address": []}'
def customerRequest = findTestObject('createUser')
def customerPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
customerRequest.setBodyContent(customerPayloadContent)
addHeaderConfiguration(customerRequest)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def userPayload = '{"id": 11, "username": "invalidStatusUser__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "12345", "phone": "12345", "userStatus": 1}'
def userRequest = findTestObject('createUser')
def userPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
userRequest.setBodyContent(userPayloadContent)
addHeaderConfiguration(userRequest)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 200)

def updatedUserPayload = '{"id": 11, "username": "invalidStatusUser__unique__", "firstName": "Jane", "lastName": "Doe", "email": "jane.doe@email.com", "password": "54321", "phone": "54321", "userStatus": 999}'
def updateUserRequest = findTestObject('updateUser')
def updatedUserPayloadContent = new HttpTextBodyContent(replaceSuffixWithUUID(updatedUserPayload))
updateUserRequest.setBodyContent(updatedUserPayloadContent)
addHeaderConfiguration(updateUserRequest)
def updateUserResponse = WSBuiltInKeywords.sendRequest(updateUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updateUserResponse, 400)

println("Step 4 - Response status code: ${updateUserResponse.getStatusCode()}")

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

