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

def customerPayload = '{"id": 1, "username": "emptyUser", "address": []}'

def createUserRequest = findTestObject('createUser')
addHeaderConfiguration(createUserRequest)
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
createUserRequest.setBodyContent(createUserPayload)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def updateUserRequest = findTestObject('updateUser', [username: 'emptyUser'])
addHeaderConfiguration(updateUserRequest)
def updateUserResponse = WSBuiltInKeywords.sendRequest(updateUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(updateUserResponse, 200)

def verifyStatusCode = updateUserResponse.getStatusCode()
if (verifyStatusCode == 0) {
    println("Step 3 - Verification: Status Code is 0")
} else {
    println("Step 3 - Verification: Status Code is not 0")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

