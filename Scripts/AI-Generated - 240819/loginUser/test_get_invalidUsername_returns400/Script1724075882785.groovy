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

def user_payload = [
    "id": 10,
    "username": "invalidUser__unique__",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com",
    "password": "12345",
    "phone": "12345",
    "userStatus": 1
]
def user_payload_json = JsonOutput.toJson(user_payload)
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(user_payload_json))
createUserRequest.setBodyContent(createUserPayload)

def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def loginUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_login/loginUser')
addHeaderConfiguration(loginUserRequest)

def loginUserResponse = WSBuiltInKeywords.sendRequest(loginUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(loginUserResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

