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

def customerPayload = '{"id": 100001, "username": "missingUser", "address": []}'
def userPayload = '{"username": "missingUser", "firstName": "Alice", "lastName": "Johnson", "email": "alice.johnson@email.com", "password": "password789", "phone": "5555555555", "userStatus": 1}'

def createUserRequest = findTestObject('createUser')
addHeaderConfiguration(createUserRequest)
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
createUserRequest.setBodyContent(createUserPayload)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def loginUserRequest = findTestObject('loginUser')
addHeaderConfiguration(loginUserRequest)
def loginUserResponse = WSBuiltInKeywords.sendRequest(loginUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(loginUserResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}
