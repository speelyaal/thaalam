import * as Hapi from '@hapi/hapi';
import Logger from './helper/logger';
import UserRoutes from './api/users/routes';
import CloudProvidersRoutes from './api/cloud-providers/routes';
import ConfigRoutes from './api/config/routes';
import RequestRoutes from './api/request/routes';
import ResponseRoutes from './api/response/routes';
import ThaalamResourcesRoutes from './api/thaalam-resources/routes';

export default class Router {
  public static async loadRoutes(server: Hapi.Server): Promise<any> {
    Logger.info('Router - Start adding routes');

    await new UserRoutes().register(server);
    await new CloudProvidersRoutes().register(server);
    await new ConfigRoutes().register(server);
    await new RequestRoutes().register(server);
    await new ResponseRoutes().register(server);
    await new ThaalamResourcesRoutes().register(server);

    Logger.info('Router - Finish adding routes');
  }
}
