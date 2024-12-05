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

def createAndVerifyUser(username, id) {
    def user_data = [
        id: id,
        username: username,
        firstName: "John",
        lastName: "Doe",
        email: "${username}@example.com",
        password: "password123",
        phone: "1234567890",
        userStatus: 1
    ]
    
    def createUserRequest = findTestObject('createUser')
    addHeaderConfiguration(createUserRequest)
    def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(JsonOutput.toJson(user_data)))
    createUserRequest.setBodyContent(createUserPayload)
    
    def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
    WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)
}

// Step 1: Create multiple Users with different usernames
for (int i = 0; i < 3; i++) {
    def username = "user_${i}__unique__"
    createAndVerifyUser(username, i)
}

// Step 2: Send a GET request to /user/logout
def logoutUserRequest = findTestObject('logoutUser')
addHeaderConfiguration(logoutUserRequest)
def logoutUserResponse = WSBuiltInKeywords.sendRequest(logoutUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(logoutUserResponse, 200)

// Step 3: Verify that the response status code is 0
WSBuiltInKeywords.verifyResponseStatusCode(logoutUserResponse, 0)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

