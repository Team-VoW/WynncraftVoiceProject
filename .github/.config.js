"use strict";
const config = require("conventional-changelog-conventionalcommits");

function whatBump() {
    const requestedReleaseType = process.env.RELEASE_TYPE;
    const validReleaseTypes = ["major", "minor", "patch"];

    if (!validReleaseTypes.includes(requestedReleaseType)) {
        throw new Error(`RELEASE_TYPE must be one of: ${validReleaseTypes.join(", ")}.`);
    }

    return {
        releaseType: requestedReleaseType,
        reason: `Release type selected when manually starting the workflow: ${requestedReleaseType}.`
    };
}

async function getOptions() {
    let options = await config(
        {
            types: [
                // Unhide all types except "ci" so that they show up on generated changelog
                // Default values:
                // https://github.com/conventional-changelog/conventional-changelog/blob/master/packages/conventional-changelog-conventionalcommits/writer-opts.js
                { type: "feat", section: "New Features" },
                { type: "feature", section: "New Features" },
                { type: "fix", section: "Bug Fixes" },
                { type: "perf", section: "Performance Improvements" },
                { type: "revert", section: "Reverts" },
                { type: "docs", section: "Documentation" },
                { type: "style", section: "Styles" },
                { type: "chore", section: "Miscellaneous Chores" },
                { type: "refactor", section: "Code Refactoring" },
                { type: "test", section: "Tests" },
                { type: "build", section: "Build System" },
                { type: "ci", section: "Continuous Integration", hidden: true },
            ]
        }
    );

    // Both of these are used in different places...
    options.recommendedBumpOpts.whatBump = whatBump;
    options.whatBump = whatBump;

    return options;
}

module.exports = getOptions();
