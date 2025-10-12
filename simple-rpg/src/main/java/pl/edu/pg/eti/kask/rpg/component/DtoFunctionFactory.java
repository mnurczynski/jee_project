package pl.edu.pg.eti.kask.rpg.component;

import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetBuildingsResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.GetOrganizationalUnitsResponse;
import pl.edu.pg.eti.kask.rpg.building.dto.PutBuildingRequest;
import pl.edu.pg.eti.kask.rpg.building.dto.function.BuildingToResponseFunction;
import pl.edu.pg.eti.kask.rpg.building.dto.function.BuildingsToResponseFunction;
import pl.edu.pg.eti.kask.rpg.building.dto.function.OrganizationalUnitToResponseFunction;
import pl.edu.pg.eti.kask.rpg.building.dto.function.OrganizationalUnitsToResponseFunction;
import pl.edu.pg.eti.kask.rpg.building.dto.function.RequestToBuildingFunction;
import pl.edu.pg.eti.kask.rpg.building.dto.function.UpdateBuildingWithRequestFunction;
import pl.edu.pg.eti.kask.rpg.building.entity.Building;
import pl.edu.pg.eti.kask.rpg.building.entity.OrganizationalUnit;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.rpg.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.rpg.user.dto.function.RequestToUserFunction;
import pl.edu.pg.eti.kask.rpg.user.dto.function.UpdateUserPasswordWithRequestFunction;
import pl.edu.pg.eti.kask.rpg.user.dto.function.UpdateUserWithRequestFunction;
import pl.edu.pg.eti.kask.rpg.user.dto.function.UserToResponseFunction;
import pl.edu.pg.eti.kask.rpg.user.dto.function.UsersToResponseFunction;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.function.Function;

/**
 * Factor for creating {@link Function} implementation for converting between various objects used in different layers.
 * Instead of injecting multiple function objects single factory is injected.
 */
public class DtoFunctionFactory {

    /**
     * Returns a function to convert a single {@link Building} to {@link GetBuildingResponse}.
     *
     * @return BuildingToResponseFunction instance
     */
    public BuildingToResponseFunction buildingToResponse() {
        return new BuildingToResponseFunction();
    }

    /**
     * Returns a function to convert a list of {@link Building} to {@link GetBuildingsResponse}.
     *
     * @return BuildingsToResponseFunction instance
     */
    public BuildingsToResponseFunction buildingsToResponse() {
        return new BuildingsToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link OrganizationalUnit} to {@link GetOrganizationalUnitResponse}.
     *
     * @return OrganizationalUnitToResponseFunction instance
     */
    public OrganizationalUnitToResponseFunction organizationalUnitToResponse() {
        return new OrganizationalUnitToResponseFunction();
    }

    /**
     * Returns a function to convert a list of {@link OrganizationalUnit} to {@link GetOrganizationalUnitsResponse}.
     *
     * @return OrganizationalUnitsToResponseFunction instance
     */
    public OrganizationalUnitsToResponseFunction organizationalUnitsToResponse() {
        return new OrganizationalUnitsToResponseFunction();
    }

    /**
     * Returns a function to convert a {@link PutBuildingRequest} to a {@link Building}.
     *
     * @return RequestToBuildingFunction instance
     */
    public RequestToBuildingFunction requestToBuilding() {
        return new RequestToBuildingFunction();
    }

    /**
     * Returns a function to update a {@link Building}.
     *
     * @return UpdateBuildingFunction instance
     */
    public UpdateBuildingWithRequestFunction updateBuilding() {
        return new UpdateBuildingWithRequestFunction();
    }

    /**
     * Returns a function to convert a {@link PutUserRequest} to a {@link User}.
     *
     * @return RequestToUserFunction instance
     */
    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    /**
     * Returns a function to update a {@link User}.
     *
     * @return UpdateUserFunction instance
     */
    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }

    /**
     * Returns a function to update a {@link User}'s password.
     *
     * @return UpdateUserPasswordFunction instance
     */
    public UpdateUserPasswordWithRequestFunction updateUserPassword() {
        return new UpdateUserPasswordWithRequestFunction();
    }

    /**
     * Returns a function to convert a list of {@link User} to {@link GetUsersResponse}.
     *
     * @return UsersToResponseFunction instance
     */
    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    /**
     * Returns a function to convert a single {@link User} to {@link GetUserResponse}.
     *
     * @return UserToResponseFunction instance
     */
    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

}
