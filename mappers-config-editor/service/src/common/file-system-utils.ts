import Logger from '../helper/logger';
import * as path from 'path';
import * as _ from 'lodash';
import * as fs from 'fs';
import dirTree = require('directory-tree');
import yaml = require('js-yaml');
import fse = require('fs-extra');

export default class FileSystemUtils {

    public static MapperRootDirRelativePath: string = process.env.MAPPER_DIR ?? '';
    public static ConfigFileName: string = process.env.CONFIG_FILE ?? '';
    public static RequestFileName: string = process.env.REQUEST_FILE ?? '';
    public static ResponseFileName: string = process.env.RESPONSE_FILE ?? '';
    public static ThaalamDirName: string = process.env.THAALAM_DIR_NAME ?? '';


    public static getSubDirectoryNames(dirInfo: dirTree.DirectoryTree): string[] {
        const subDirs = _.filter(dirInfo.children, { type: "directory" });
        const dirNames = subDirs.map(e => e.name);
        return dirNames;
    }

    public static isPathExists(path: string): boolean {
        return fs.existsSync(path);
    }

    public static getFileAsObject(filePath: string): any {
        try {
            const fileContent = fs.readFileSync(filePath, 'utf8');
            return yaml.safeLoad(fileContent);

        } catch (error) {
            Logger.error(`File: FileSystemUtils, Method: getFileAsObject - Exception while converting Yaml to Object. 
                     Given file path: ${filePath}`);
            return {};
        }
    }

    public static setObjectAsFile(filePath: string, fileObject: any): boolean {
        try {
            const yamlContent = yaml.safeDump(fileObject);
            FileSystemUtils.writeToFile(filePath, yamlContent);
            return true;

        } catch (error) {
            Logger.error(`File: FileSystemUtils, Method: setObjectAsFile - Exception while converting Object to Yaml. 
                     Given file object: ${JSON.stringify(fileObject)}`);
            return false;
        }
    }

    public static writeToFile(filePath: string, content: string) {
        fs.writeFile(filePath, content, (err) => {
            if (err) {
                Logger.error(`File: FileSystemUtils, Method: writeToFile - Exception while writing to file. 
                Given file path: ${filePath}, Given file content: ${content}, error: ${JSON.stringify(err)}`);
            }
        });
    }

    public static async GetMappersDirAsObject(): Promise<dirTree.DirectoryTree> {
        return new Promise((resolve, reject) => {
            try {
                const rootDirPath = this.GetMappersRootDirectoryPath();
                const dirAsObject = dirTree(rootDirPath);
                resolve(dirAsObject);

            } catch (error) {
                Logger.info(`Exception - Method: GetMappersDirAsObject - Error: ${JSON.stringify(error)}`);
                reject(error);
            }
        });
    }

    public static GetMappersRootDirectoryPath(): string {
        return path.resolve(FileSystemUtils.MapperRootDirRelativePath);
    }

    public static getCloudProviderDirPath(cloudProviderId: string): string {
        return path.join(FileSystemUtils.GetMappersRootDirectoryPath(), cloudProviderId);
    }

    public static getConfigFilePath(cloudProviderId: string): string {
        return path.join(FileSystemUtils.GetMappersRootDirectoryPath(), cloudProviderId, FileSystemUtils.ConfigFileName);
    }

    public static getRequestFilePath(cloudProviderId: string, resourceId: string): string {
        return path.join(FileSystemUtils.GetMappersRootDirectoryPath(), cloudProviderId,
            "resources", resourceId, FileSystemUtils.RequestFileName);
    }

    public static getResponseFilePath(cloudProviderId: string, resourceId: string): string {
        return path.join(FileSystemUtils.GetMappersRootDirectoryPath(), cloudProviderId,
            "resources", resourceId, FileSystemUtils.ResponseFileName);
    }

    public static getThaalamConfigFilePath(): string {
        return path.join(FileSystemUtils.GetMappersRootDirectoryPath(),
            FileSystemUtils.ThaalamDirName, FileSystemUtils.ConfigFileName);
    }

    public static getThaalamTemplateDirPath(): string {
        return path.join(FileSystemUtils.GetMappersRootDirectoryPath(),
            FileSystemUtils.ThaalamDirName, "template");
    }

    public static copyTemplateFilesForCloudProvider(cloudProviderId: string): boolean {
        try {
            // create folder for "new cloud provider"
            const cloudDirPath = FileSystemUtils.getCloudProviderDirPath(cloudProviderId);
            fse.ensureDirSync(cloudDirPath);

            // copy files from "template" to "new folder"
            const templateDirPath = FileSystemUtils.getThaalamTemplateDirPath();
            fse.copySync(templateDirPath, cloudDirPath);

            return true;

        } catch (err) {
            Logger.error(`File: FileSystemUtils, Method: copyTemplateFilesForCloudProvider - Exception occured. 
                            Given cloudProviderId: ${cloudProviderId}`);
            return false;
        }
    }
}