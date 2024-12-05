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

def createUserRequest = findTestObject('createUser')
addHeaderConfiguration(createUserRequest)

def create_user_payload = '{"id": 1, "username": "existingUser__unique__", "firstName": "John", "lastName": "Doe", "email": "john.doe@email.com", "password": "password123", "phone": "1234567890", "userStatus": 1}'
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(create_user_payload))
createUserRequest.setBodyContent(createUserPayload)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def getUserRequest = findTestObject('getUserByName', ['username': 'existingUser'])
addHeaderConfiguration(getUserRequest)
def getUserResponse = WSBuiltInKeywords.sendRequest(getUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getUserResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

