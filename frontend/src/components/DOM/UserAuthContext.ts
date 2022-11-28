import { createContext } from "react"
import { UserAutorizedRoles } from "../../constants/types";


const UserAuthContext = createContext<UserAutorizedRoles>({ isAuthorized: false });

export default UserAuthContext;