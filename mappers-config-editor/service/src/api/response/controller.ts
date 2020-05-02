import * as Hapi from '@hapi/hapi';
import * as Boom from '@hapi/boom';
import Logger from '../../helper/logger';
import newResponse from '../../helper/response';
import * as _ from 'lodash';
import fsUtils from '../../common/file-system-utils';


export default class ResponseController {
  constructor() {
  }

  public getResponseById = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`GET - ${request.url.href}`);
      const id = encodeURIComponent(request.params["id"]);
      const resourceId = encodeURIComponent(request.params["resourceId"]);
      let response: any = {};

      const cloudDirPath = fsUtils.getCloudProviderDirPath(id);
      const responseFilePath = fsUtils.getResponseFilePath(id, resourceId);

      // Cloud Directory path - validation
      if (!fsUtils.isPathExists(cloudDirPath)) {
        Logger.error(`Controller: ResponseController, Method: getResponseById - Cloud Dir path Not found. 
                           Given cloud dir path: ${cloudDirPath}`);
        return toolkit.response(
          newResponse(request, {
            boom: Boom.notFound(),
          })
        );
      }

      // Response file path - validation
      if (!fsUtils.isPathExists(responseFilePath)) {
        Logger.error(`Controller: ResponseController, Method: getResponseById - Response File Not found. 
                     Given file path: ${responseFilePath}`);
        return toolkit.response(
          newResponse(request, {
            value: response,
          })
        );
      }

      //Convert Yaml as Object
      response = fsUtils.getFileAsObject(responseFilePath);

      return toolkit.response(
        newResponse(request, {
          value: response,
        })
      );

    } catch (error) {
      return toolkit.response(
        newResponse(request, {
          boom: Boom.badImplementation(error),
        })
      );
    }
  };

  public updateResponseById = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`PUT - ${request.url.href}`);
      const id = encodeURIComponent(request.params["id"]);
      const resourceId = encodeURIComponent(request.params["resourceId"]);
      const payload = request.payload;
      const cloudDirPath = fsUtils.getCloudProviderDirPath(id);

      // Cloud Directory path - validation
      if (!fsUtils.isPathExists(cloudDirPath)) {
        Logger.error(`Controller: ResponseController, Method: updateResponseById - Cloud Dir path Not found. 
                                 Given cloud dir path: ${cloudDirPath}`);
        return toolkit.response(
          newResponse(request, {
            boom: Boom.notFound(),
          })
        );
      }
      
      const responseFilePath = fsUtils.getResponseFilePath(id, resourceId);
      const isWriteSuccess = fsUtils.setObjectAsFile(responseFilePath, payload);

      // Failure case
      if (!isWriteSuccess) {
        return toolkit.response(
          newResponse(request, {
            boom: Boom.badImplementation(),
          })
        );
      }

      // Sucess Case
      return toolkit.response(
        newResponse(request, {
          value: isWriteSuccess,
        })
      );
    } catch (error) {
      return toolkit.response(
        newResponse(request, {
          boom: Boom.badImplementation(error),
        })
      );
    }
  };

}
