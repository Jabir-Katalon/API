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

def endpoint_step1 = "/user/nonExistingUser"
def testObject_step1 = findTestObject("Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_user_username_/getUserByName")
def request_step1 = testObject_step1
addHeaderConfiguration(request_step1)
def response_step1 = WSBuiltInKeywords.sendRequest(request_step1)
println("Step 1 - GET ${endpoint_step1}")
println("Response Status Code: ${response_step1.getStatusCode()}")

def expected_status_code_step2 = 400
println("Step 2 - Verify that the response status code is ${expected_status_code_step2}")
if (WSBuiltInKeywords.verifyResponseStatusCode(response_step1, expected_status_code_step2)) {
    println("Verification Successful")
} else {
    println("Verification Failed")
}

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

