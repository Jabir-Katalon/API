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

def customerPayload = '{"id": 1, "username": "noPasswordUser", "address": []}'
def userPayload = '{"username": "noPasswordUser", "firstName": "Sarah", "lastName": "Brown", "email": "sarah.brown@email.com", "password": "passwordabc", "phone": "1112223333", "userStatus": 1}'

def createUserRequest = findTestObject('createUser')
addHeaderConfiguration(createUserRequest)
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
createUserRequest.setBodyContent(createUserPayload)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def getUserLoginRequest = findTestObject('loginUser')
addHeaderConfiguration(getUserLoginRequest)
def getUserLoginResponse = WSBuiltInKeywords.sendRequest(getUserLoginRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getUserLoginResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

