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

def customerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "username": "statusUser", "address": []}'))
def createCustomerRequest = findTestObject('createUser')
createCustomerRequest.setBodyContent(customerPayload)
addHeaderConfiguration(createCustomerRequest)
def createCustomerResponse = WSBuiltInKeywords.sendRequest(createCustomerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)

def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"id": 1, "username": "statusUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password", "phone": "123456789", "userStatus": -1}'))
def updateUserRequest = findTestObject('updateUser')
updateUserRequest.setBodyContent(userPayload)
addHeaderConfiguration(updateUserRequest)
def updateUserResponse = WSBuiltInKeywords.sendRequest(updateUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updateUserResponse, 200)

if (updateUserResponse.getStatusCode() == 0) {
    println("Step 3 - Response status code is 0")
} else {
    println("Step 3 - Response status code is not 0")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

