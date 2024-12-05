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

def categoryPayload = '{"id": 2, "name": "Cats"}'
def petPayload = '{"id": 11, "name": "kitty", "category": {"id": 2}, "photoUrls": ["url1", "url2"], "status": "available"}'
def orderPayload = '{"id": 11, "petId": 11, "quantity": 5, "shipDate": "2022-01-01T00:00:00Z", "status": "placed", "complete": false}'

def addCategoryRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
addHeaderConfiguration(addCategoryRequest)
addCategoryRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(categoryPayload)))
def addCategoryResponse = WSBuiltInKeywords.sendRequest(addCategoryRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addCategoryResponse, 200)

def addPetRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_pet/addPet')
addHeaderConfiguration(addPetRequest)
addPetRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(petPayload)))
def addPetResponse = WSBuiltInKeywords.sendRequest(addPetRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addPetResponse, 200)

def addOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_store_order/placeOrder')
addHeaderConfiguration(addOrderRequest)
addOrderRequest.setBodyContent(new HttpTextBodyContent(replaceSuffixWithUUID(orderPayload)))
def addOrderResponse = WSBuiltInKeywords.sendRequest(addOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(addOrderResponse, 200)

def getOrderRequest = findTestObject('Object Repository/Swagger Petstore - OpenAPI 3_0 (1)/_store_order_orderId_/getOrderById', ['orderId': '11'])
def getOrderResponse = WSBuiltInKeywords.sendRequest(getOrderRequest)
WSBuiltInKeywords.verifyResponseStatusCode(getOrderResponse, 200)

def replaceSuffixWithUUID(payload) {
    replacedString = payload.replaceAll('unique__', uuid)
    return replacedString
}

