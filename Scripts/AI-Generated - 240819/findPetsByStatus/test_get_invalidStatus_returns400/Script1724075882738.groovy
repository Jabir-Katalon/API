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

def endpoint = "/pet/findByStatus"
def query_param = "status=invalidStatus"
def testObject = findTestObject("Object Repository/Swagger Petstore - OpenAPI 3_0 (4)/_pet_findByStatus/findPetsByStatus")
addHeaderConfiguration(testObject)
def url = testObject.getRestUrl() + "?" + query_param
def response = WSBuiltInKeywords.sendRequest(testObject)

WSBuiltInKeywords.verifyResponseStatusCode(response, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

