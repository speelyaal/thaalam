import * as Hapi from '@hapi/hapi';
import * as Boom from '@hapi/boom';
import Logger from '../../helper/logger';
import newResponse from '../../helper/response';
import * as _ from 'lodash';
import fsUtils from '../../common/file-system-utils';


export default class RequestController {
  constructor() {
  }

  public getRequestById = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`GET - ${request.url.href}`);
      const id = encodeURIComponent(request.params["id"]);
      const resourceId = encodeURIComponent(request.params["resourceId"]);
      let response: any = {};

      const cloudDirPath = fsUtils.getCloudProviderDirPath(id);
      const requestFilePath = fsUtils.getRequestFilePath(id, resourceId);

      // Cloud Directory path - validation
      if (!fsUtils.isPathExists(cloudDirPath)) {
        Logger.error(`Controller: RequestController, Method: getRequestById - Cloud Dir path Not found. 
                           Given cloud dir path: ${cloudDirPath}`);
        return toolkit.response(
          newResponse(request, {
            boom: Boom.notFound(),
          })
        );
      }

      // Request file path - validation
      if (!fsUtils.isPathExists(requestFilePath)) {
        Logger.error(`Controller: RequestController, Method: getRequestById - Request File Not found. 
                     Given file path: ${requestFilePath}`);
        return toolkit.response(
          newResponse(request, {
            value: response,
          })
        );
      }

      //Convert Yaml as Object
      response = fsUtils.getFileAsObject(requestFilePath);

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

  public updateRequestById = async (
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
        Logger.error(`Controller: RequestController, Method: updateRequestById - Cloud Dir path Not found. 
                                 Given cloud dir path: ${cloudDirPath}`);
        return toolkit.response(
          newResponse(request, {
            boom: Boom.notFound(),
          })
        );
      }
      
      const requestFilePath = fsUtils.getRequestFilePath(id, resourceId);
      const isWriteSuccess = fsUtils.setObjectAsFile(requestFilePath, payload);

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
