/*
 * Copyright (C) 2004, 2005 Ben Mazur (bmazur@sev.org)
 * Copyright (C) 2007-2025 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 *
 * MechWarrior, BattleMech, `Mech and AeroTech are registered trademarks
 * of The Topps Company, Inc. All Rights Reserved.
 *
 * Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of
 * InMediaRes Productions, LLC.
 *
 * MechWarrior Copyright Microsoft Corporation. MegaMek was created under
 * Microsoft's "Game Content Usage Rules"
 * <https://www.xbox.com/en-US/developers/rules> and it is not endorsed by or
 * affiliated with Microsoft.
 */

package megamek.common.weapons.flamers.innerSphere;

/**
 * @author Andrew Hunter
 * @since Sep 24, 2004
 */
public class ISXFlamer extends megamek.common.weapons.flamers.FlamerWeapon {
    @java.io.Serial
    private static final long serialVersionUID = 1514639280093120062L;

    public ISXFlamer() {
        super();
        name = "Flamer (X)";
        setInternalName(this.name);
        addLookupName("IS Flamer (X)");
        addLookupName("ISXFlamer");
        heat = 2;
        damage = 4;
        infDamageClass = megamek.common.equipment.WeaponType.WEAPON_BURST_6D6;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 0.5;
        criticalSlots = 1;
        bv = 6;
        cost = 7500;
        shortAV = 2;
        maxRange = RANGE_SHORT;
        atClass = CLASS_POINT_DEFENSE;
        rulesRefs = "218, TM";
        techAdvancement.setTechBase(megamek.common.enums.TechBase.ALL)
              .setIntroLevel(true)
              .setUnofficial(false)
              .setTechRating(megamek.common.enums.TechRating.C)
              .setAvailability(megamek.common.enums.AvailabilityValue.B, megamek.common.enums.AvailabilityValue.B, megamek.common.enums.AvailabilityValue.B, megamek.common.enums.AvailabilityValue.A)
              .setISAdvancement(DATE_ES, DATE_ES, DATE_ES, DATE_NONE, DATE_NONE)
              .setISApproximate(false, false, false, false, false)
              .setClanAdvancement(DATE_ES, DATE_ES, DATE_ES, 2830, DATE_NONE)
              .setClanApproximate(false, false, false, false, false);
    }
}
