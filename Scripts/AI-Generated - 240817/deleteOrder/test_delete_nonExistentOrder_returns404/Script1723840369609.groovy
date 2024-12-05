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

def petPayload = '{"name": "doggie__unique__", "photoUrls": ["https://example.com/photo1"]}'
def orderPayload = '{"id": 9999, "petId": 1, "quantity": 1, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": false}'

def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
def addPetPayload = new HttpTextBodyContent(replaceSuffixWithUUID(petPayload))
addHeaderConfiguration(addPetRequest)
addPetRequest.setBodyContent(addPetPayload)
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def addOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_store_order/placeOrder')
def addOrderPayload = new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload))
addHeaderConfiguration(addOrderRequest)
addOrderRequest.setBodyContent(addOrderPayload)
def addOrderResponse = WSBuiltInKeywords.sendRequest(addOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addOrderResponse, 200)

def deleteOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_store_order_orderId_/deleteOrder', [orderId: 9999])
addHeaderConfiguration(deleteOrderRequest)
def deleteOrderResponse = WSBuiltInKeywords.sendRequest(deleteOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(deleteOrderResponse, 404)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

