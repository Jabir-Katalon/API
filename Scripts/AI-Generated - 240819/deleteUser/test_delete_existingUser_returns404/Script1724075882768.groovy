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

def create_user_payload = '{"id": 1, "username": "existingUser__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(create_user_payload))
createUserRequest.setBodyContent(createUserPayload)

def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def deleteUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_username_/deleteUser', ['username': 'existingUser'])
addHeaderConfiguration(deleteUserRequest)

def deleteUserResponse = WSBuiltInKeywords.sendRequest(deleteUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteUserResponse, 404)

if (deleteUserResponse.getStatusCode() == 404) {
    println("Step 3 - Verification: Response status code is 404 - Test Passed")
} else {
    println("Step 3 - Verification: Response status code is not 404 - Test Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

