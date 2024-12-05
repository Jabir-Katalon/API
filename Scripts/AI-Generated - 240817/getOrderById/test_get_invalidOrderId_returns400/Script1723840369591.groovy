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

def getOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_store_order_orderId_/getOrderById')
def orderId = 1000
def variables = ['orderId': orderId]
def getOrderResponse

addHeaderConfiguration(getOrderRequest)
getOrderResponse = WSBuiltInKeywords.sendRequest(findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_store_order_orderId_/getOrderById', variables))

WSBuiltInKeywords.verifyResponseStatusCode(getOrderResponse, 400)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

