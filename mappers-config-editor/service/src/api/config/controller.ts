import * as Hapi from '@hapi/hapi';
import * as Boom from '@hapi/boom';
import Logger from '../../helper/logger';
import newResponse from '../../helper/response';
import * as _ from 'lodash';
import fsUtils from '../../common/file-system-utils';


export default class ConfigController {
  constructor() {
  }

  public getConfigById = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`GET - ${request.url.href}`);
      const id = encodeURIComponent(request.params["id"]);
      let response: any = {};

      const cloudDirPath = fsUtils.getCloudProviderDirPath(id);
      const configPath = fsUtils.getConfigFilePath(id);

      // Cloud Directory path - validation
      if (!fsUtils.isPathExists(cloudDirPath)) {
        Logger.error(`Controller: ConfigController, Method: getConfigById - Cloud Dir path Not found. 
                           Given cloud dir path: ${cloudDirPath}`);
        return toolkit.response(
          newResponse(request, {
            boom: Boom.notFound(),
          })
        );
      }

      // Config file path - validation
      if (!fsUtils.isPathExists(configPath)) {
        Logger.error(`Controller: ConfigController, Method: getConfigById - Config File Not found. 
                     Given config file path: ${configPath}`);
        return toolkit.response(
          newResponse(request, {
            value: response,
          })
        );
      }

      //Convert Yaml as Object
      response = fsUtils.getFileAsObject(configPath);

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

  public updateConfigById = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`PUT - ${request.url.href}`);
      const id = encodeURIComponent(request.params["id"]);
      const payload = request.payload;
      const cloudDirPath = fsUtils.getCloudProviderDirPath(id);

      // Cloud Directory path - validation
      if (!fsUtils.isPathExists(cloudDirPath)) {
        Logger.error(`Controller: ConfigController, Method: updateConfigById - Cloud Dir path Not found. 
                                 Given cloud dir path: ${cloudDirPath}`);
        return toolkit.response(
          newResponse(request, {
            boom: Boom.notFound(),
          })
        );
      }
      
      const configPath = fsUtils.getConfigFilePath(id);
      const isWriteSuccess = fsUtils.setObjectAsFile(configPath, payload);

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
