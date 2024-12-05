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

def createUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user/createUser')
addHeaderConfiguration(createUserRequest)

def customer_payload = '{"id": 1, "username": "missingUser", "address": []}'
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customer_payload))
createUserRequest.setBodyContent(createUserPayload)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def updateUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_username_/updateUser', ['username': 'missingUser'])
addHeaderConfiguration(updateUserRequest)

def user_payload = '{"id": 1, "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'
def updateUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(user_payload))
updateUserRequest.setBodyContent(updateUserPayload)
def updateUserResponse = WSBuiltInKeywords.sendRequest(updateUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updateUserResponse, 200)

if (updateUserResponse.getStatusCode() == 0) {
    println("Step 3 - Verification: Status Code is 0")
} else {
    println("Step 3 - Verification: Status Code is not 0")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

