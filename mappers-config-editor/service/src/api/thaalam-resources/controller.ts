import * as Hapi from '@hapi/hapi';
import * as Boom from '@hapi/boom';
import Logger from '../../helper/logger';
import newResponse from '../../helper/response';
import * as _ from 'lodash';
import fsUtils from '../../common/file-system-utils';


export default class ThaalamResourcesController {
  constructor() {
  }

  public getThaalamResources = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`GET - ${request.url.href}`);
      let response: any = {};

      const thaalamConfigFilePath = fsUtils.getThaalamConfigFilePath();

      // config file path - validation
      if (!fsUtils.isPathExists(thaalamConfigFilePath)) {
        Logger.error(`Controller: ThaalamResourcesController, Method: getThaalamResources - thaalam config file Not found. 
                           Given file path: ${thaalamConfigFilePath}`);
        return toolkit.response(
          newResponse(request, {
            boom: Boom.notFound(),
          })
        );
      }

      //Convert Yaml as Object
      response = fsUtils.getFileAsObject(thaalamConfigFilePath);

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

}
