#set(withLombok = entityConfig.isWithLombok())
#set(withSwagger = entityConfig.isWithSwagger())
#set(swaggerVersion = entityConfig.getSwaggerVersion())
#set(entityClassName = index.buildEntityClassName())
package #(packageConfig.entityPackage);

#for(importClass : index.buildImports())
import #(importClass);
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
import io.swagger.v3.oas.annotations.media.Schema;
#end
#if(withLombok)
import lombok.Data;
#end

/**
 * 实体类。
 *
 * @author #(javadocConfig.getAuthor())
 * @since #(javadocConfig.getSince())
 */
#if(withLombok)
@Data
#end
#if(withSwagger && swaggerVersion.getName() == "FOX")
@ApiModel("#(entityClassName)")
#end
#if(withSwagger && swaggerVersion.getName() == "DOC")
@Schema(description = "#(entityClassName)")
#end
#(index.buildIndexAnnotation())
public class #(entityClassName) #(index.buildExtends())#(index.buildImplements()) {

#for(column : index.columns)
    #set(annotations = column.buildAnnotations())
    #if(isNotBlank(annotations))
    #(annotations)
    #end
    #if(withSwagger && swaggerVersion.getName() == "FOX")
    @ApiModelProperty("#(column.property)")
    #end
    #if(withSwagger && swaggerVersion.getName() == "DOC")
    @Schema(description = "#(column.property)")
    #end
    private #(column.propertySimpleType) #(column.property);

#end
#if(!withLombok)
    #for(column: index.columns)
    public #(column.propertySimpleType) #(column.getterMethod())() {
        return #(column.property);
    }

     public void #(column.setterMethod())(#(column.propertySimpleType) #(column.property)) {
        this.#(column.property) = #(column.property);
    }

    #end
#end}
