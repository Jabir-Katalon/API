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

def customerPayload = '{"id": 100001, "username": "testCustomer__unique__", "address": []}'
def userPayload = '{"id": 11, "username": "testUser__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'
def updatedUserPayload = '{"firstName": "Jane", "lastName": "Smith"}'

def createCustomerRequest = findTestObject('createUser')
def createCustomerPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
createCustomerRequest.setBodyContent(createCustomerPayload)
addHeaderConfiguration(createCustomerRequest)
def createCustomerResponse = WSBuiltInKeywords.sendRequest(createCustomerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)

def createUserRequest = findTestObject('createUser')
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
createUserRequest.setBodyContent(createUserPayload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def updateUserRequest = findTestObject('updateUser', ['username': 'testUser'])
def updateUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(updatedUserPayload))
updateUserRequest.setBodyContent(updateUserPayload)
addHeaderConfiguration(updateUserRequest)
def updateUserResponse = WSBuiltInKeywords.sendRequest(updateUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updateUserResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

