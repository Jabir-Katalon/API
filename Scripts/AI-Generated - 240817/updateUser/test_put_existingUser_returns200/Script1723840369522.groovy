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

def customerPayload = '{"id": 100000, "username": "existingCustomer__unique__", "address": []}'
def userPayload = '{"id": 10, "username": "existingUser__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "12345", "phone": "12345", "userStatus": 1}'
def updatedUserPayload = '{"firstName": "Jane", "lastName": "Smith"}'

def createCustomerRequest = findTestObject('createUser')
def createUserRequest = findTestObject('createUser')
def updateUserRequest = findTestObject('updateUser')

def createCustomerPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
def updatePayload = new HttpTextBodyContent(replaceSuffixWithUUID(updatedUserPayload))

addHeaderConfiguration(createCustomerRequest)
addHeaderConfiguration(createUserRequest)
addHeaderConfiguration(updateUserRequest)

createCustomerRequest.setBodyContent(createCustomerPayload)
createUserRequest.setBodyContent(createUserPayload)
updateUserRequest.setBodyContent(updatePayload)

def createCustomerResponse = WSBuiltInKeywords.sendRequest(createCustomerRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
def updateUserResponse = WSBuiltInKeywords.sendRequest(updateUserRequest)

WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)
WSBuiltInKeywords.verifyResponseStatusCode(updateUserResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

