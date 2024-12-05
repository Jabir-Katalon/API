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

def response

// Step 1: Delete all existing Users
def deleteUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_username_/deleteUser')
addHeaderConfiguration(deleteUserRequest)
response = WSBuiltInKeywords.sendRequest(deleteUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(response, 200)

// Step 2: Send a GET request to /user/logout
def logoutUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_logout/logoutUser')
addHeaderConfiguration(logoutUserRequest)
response = WSBuiltInKeywords.sendRequest(logoutUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(response, 200)

// Step 3: Verify that the response status code is 0
if (response.getStatusCode() == 0) {
    println("Step 3 - Verify that the response status code is 0: Passed")
} else {
    println("Step 3 - Verify that the response status code is 0: Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

