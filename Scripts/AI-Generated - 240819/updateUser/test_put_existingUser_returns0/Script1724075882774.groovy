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

def customerPayload = '{"id": 1, "username": "existingUser", "address": []}'
def userPayload = '{"id": 1, "username": "existingUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'
def putPayload = '{"id": 1, "username": "existingUser", "firstName": "Jane", "lastName": "Doe", "email": "jane.doe@email.com", "password": "newpassword123", "phone": "9876543210", "userStatus": 2}'

def createUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user/createUser')
def updateUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_username_/updateUser')

addHeaderConfiguration(createUserRequest)
addHeaderConfiguration(updateUserRequest)

def createCustomerResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createCustomerResponse, 200)

def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def updateUserResponse = WSBuiltInKeywords.sendRequest(updateUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updateUserResponse, 0)

println(updateUserResponse.getStatusCode())

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

