

JSONObject
MappingFile.yaml
ObjectMapper
Transformer<T>
	Target Object



Thaalam Object Structure is always the reference for the mapping
Each mapping yaml follows the structure of Thaalam Object and maps it to the target json object of a cloud provider. 
Each Thaalam Object will have 3 mapping files. 

TransformerConfig.yaml
requestMapping.yaml
responseMapping.yaml

For the list the transformer needs to be notified where the list is.. at root level? or under a property? 



Flow 

1.  The Transformer is initiated with a Type for example VirtualMachine
2.  The transformer need to be configured where to look for resource files (transformerConfig.yaml)
3. 


Flow for client
1. Create a package for each provicer
2. Create a OpenFeign client for each type(VM, Network, keys) in that package
3. Create Transformers to transfrom from and to Thaalam Object (request, response, list response)