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

def customerPayload = '{"id": 9999, "username": "testUser", "address": []}'
def userPayload = '{"id": 10, "username": "testUser", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "12345", "phone": "12345", "userStatus": 1}'

def createUserRequest = findTestObject('createUser')
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(userPayload))
createUserRequest.setBodyContent(createUserPayload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def deleteUserRequest = findTestObject('deleteUser', ['username': 'testUser'])
addHeaderConfiguration(deleteUserRequest)
def deleteUserResponse = WSBuiltInKeywords.sendRequest(deleteUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteUserResponse, 404)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

