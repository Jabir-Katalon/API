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

def customerPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"username": "testCustomer"}'))
def customerRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user/createUser')
customerRequest.setBodyContent(customerPayload)
addHeaderConfiguration(customerRequest)
def customerResponse = WSBuiltInKeywords.sendRequest(customerRequest)
WSBuiltInKeywords.verifyResponseStatusCode(customerResponse, 200)

def userPayload = new HttpTextBodyContent(replaceSuffixWithUUID('{"username": "duplicateUser"}'))
def userRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user/createUser')
userRequest.setBodyContent(userPayload)
addHeaderConfiguration(userRequest)
def userResponse = WSBuiltInKeywords.sendRequest(userRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userResponse, 200)

def userListPayload = new HttpTextBodyContent(replaceSuffixWithUUID('[{"username": "duplicateUser"}, {"username": "duplicateUser"}]'))
def userListRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_user_createWithList/createUsersWithListInput')
userListRequest.setBodyContent(userListPayload)
addHeaderConfiguration(userListRequest)
def userListResponse = WSBuiltInKeywords.sendRequest(userListRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userListResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

