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

def customerPayload = '{"id": 100001, "username": "testUser__unique__", "address": ["123 Test St."]}'

def createUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/createUser')
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
createUserRequest.setBodyContent(createUserPayload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def getUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_username_/getUserByName', ['username': 'testUser__unique__'])
addHeaderConfiguration(getUserRequest)
def getUserResponse = WSBuiltInKeywords.sendRequest(getUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getUserResponse, 200)

if (getUserResponse.getStatusCode() == 200) {
    println("Step 3 - Verification: Status Code is 200 - PASSED")
} else {
    println("Step 3 - Verification: Status Code is not 200 - FAILED")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

