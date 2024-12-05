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

def customerPayload = '{"id": 1, "username": "testCustomer", "address": []}'
def createUserRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user/createUser')
def createUserPayload = new HttpTextBodyContent(replaceSuffixWithUUID(customerPayload))
createUserRequest.setBodyContent(createUserPayload)
addHeaderConfiguration(createUserRequest)
def createUserResponse = WSBuiltInKeywords.sendRequest(createUserRequest)
WSBuiltInKeywords.verifyResponseStatusCode(createUserResponse, 200)

def userCreateWithListRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_createWithList/createUsersWithListInput')
def userCreateWithListPayload = new HttpTextBodyContent(replaceSuffixWithUUID('[]'))
userCreateWithListRequest.setBodyContent(userCreateWithListPayload)
addHeaderConfiguration(userCreateWithListRequest)
def userCreateWithListResponse = WSBuiltInKeywords.sendRequest(userCreateWithListRequest)
WSBuiltInKeywords.verifyResponseStatusCode(userCreateWithListResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

