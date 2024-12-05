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

def customerRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user/createUser')
addHeaderConfiguration(customerRequest)
def customerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"username": "existingUser", "userStatus": 1}'))
customerRequest.setBodyContent(customerPayload)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def userRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_username_/getUserByName', ['username': 'existingUser'])
addHeaderConfiguration(userRequest)
def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"username": "existingUser", "userStatus": 1}'))
userRequest.setBodyContent(userPayload)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 400)

if (userResponse.getStatusCode() == 400) {
    println("Step 3 - Response status code is 400 - Test Passed")
} else {
    println("Step 3 - Response status code is not 400 - Test Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

