import * as Hapi from '@hapi/hapi';
import * as Boom from '@hapi/boom';
import Logger from '../../helper/logger';
import newResponse from '../../helper/response';
import * as path from 'path';
import * as _ from 'lodash';
import dirTree = require('directory-tree');
import fs = require('fs');
import yaml = require('js-yaml');
import fsUtils from '../../common/file-system-utils';



export default class CloudProviderController {
  constructor() {
  }

  public testDriver = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`GET - ${request.url.href}`);

      //const entities: T[] = await this.crudResolver.getAll();
      const entities: string[] = ['one', 'two'];

      const dirRelativePath: string = `${process.env.MAPPER_DIR}`;
      const mapperDirPath = path.resolve(dirRelativePath);

      entities.push(dirRelativePath);
      entities.push(mapperDirPath);
      const tree = dirTree(mapperDirPath);
      entities.push(JSON.stringify(tree));

      const cloudProviderDirs = _.filter(tree.children, { type: "directory" });
      const dirNames = cloudProviderDirs.map(e => e.name);

      let tempPath = '';
      let fileContent = '';
      let yamlAsObject;
      if (tree.children) {
        tempPath = path.join(tree.children[0].path, "config.yml");
        fileContent = fs.readFileSync(tempPath, 'utf8');
        yamlAsObject = yaml.safeLoad(fileContent);
      }

      const tempResult = {
        "topLevelDirs": dirNames,
        "firstConfigPath": tempPath,
        "fileContent": fileContent,
        "yamlAsObject": yamlAsObject
      }



      return toolkit.response(
        newResponse(request, {
          value: tempResult,
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

  public getAll = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`GET - ${request.url.href}`);

      const mappersInfo = await fsUtils.GetMappersDirAsObject();
      const subDirNames = fsUtils.getSubDirectoryNames(mappersInfo);

      // Remove Thaalam Dir name from list.
      _.remove(subDirNames, e => {
        return (e == fsUtils.ThaalamDirName);        
      });

      return toolkit.response(
        newResponse(request, {
          value: subDirNames,
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

  public createCloudProvider = async (
    request: Hapi.Request,
    toolkit: Hapi.ResponseToolkit
  ): Promise<any> => {
    try {
      Logger.info(`POST - ${request.url.href}`);
      const payload: any = request.payload;
      const newCloudProviderName: string = payload.name;

      const response = fsUtils.copyTemplateFilesForCloudProvider(newCloudProviderName);

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
